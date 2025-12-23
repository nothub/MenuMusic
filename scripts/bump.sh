#!/usr/bin/env bash

set -euo pipefail

# go to project root
cd "$(dirname "$(realpath "$0")")/.."

minecraft_version="$(curl -fsSL "https://launchermeta.mojang.com/mc/game/version_manifest.json" \
    | jq -r '.versions | map(select(.type == "release")) | .[0].id')"
: "${minecraft_version:?"Failed to fetch minecraft version!"}"
echo >&2 "minecraft_version     = ${minecraft_version}"

yarn_mappings_version="$(curl -fsSL "https://meta.fabricmc.net/v2/versions/yarn/${minecraft_version}" \
    | jq -r '[.[] | select(.stable==true)][0].version // .[0].version')"
: "${yarn_mappings_version:?"Failed to fetch yarn mappings version!"}"
echo >&2 "yarn_mappings_version = ${yarn_mappings_version}"

fabric_loader_version="$(curl -fsSL "https://meta.fabricmc.net/v2/versions/loader/${minecraft_version}" \
    | jq -r '[.[] | select(.loader.stable==true)][0].loader.version // .[0].loader.version')"
: "${fabric_loader_version:?"Failed to fetch fabric loader version!"}"
echo >&2 "fabric_loader_version = ${fabric_loader_version}"

fabric_api_version="$(curl -fsSL --get "https://api.modrinth.com/v2/project/fabric-api/version" \
    --data-urlencode 'loaders=["fabric"]' \
    --data-urlencode "game_versions=[\"${minecraft_version}\"]" \
    --data-urlencode 'featured=true' \
    | jq -r '.[0].version_number')"
: "${fabric_api_version:?"Failed to fetch fabric api version!"}"
echo >&2 "fabric_api_version    = ${fabric_api_version}"

./gradlew clean

changes=()

if ! cat gradle.properties | grep -qF "minecraft_version=${minecraft_version}"; then
    sed -i "s/^minecraft_version=.*$/minecraft_version=${minecraft_version}/" gradle.properties
    changes+=("Bump Minecraft to v${minecraft_version}")
    echo >&2 "${changes[-1]}"
fi

if ! cat gradle.properties | grep -qF "yarn_mappings_version=${yarn_mappings_version}"; then
    sed -i "s/^yarn_mappings_version=.*$/yarn_mappings_version=${yarn_mappings_version}/" gradle.properties
    changes+=("Bump Yarn mappings to v${yarn_mappings_version}")
    echo >&2 "${changes[-1]}"
fi

if ! cat gradle.properties | grep -qF "fabric_loader_version=${fabric_loader_version}"; then
    sed -i "s/^fabric_loader_version=.*$/fabric_loader_version=${fabric_loader_version}/" gradle.properties
    changes+=("Bump Fabric loader to v${fabric_loader_version}")
    echo >&2 "${changes[-1]}"
fi

if ! cat gradle.properties | grep -qF "fabric_api_version=${fabric_api_version}"; then
    sed -i "s/^fabric_api_version=.*$/fabric_api_version=${fabric_api_version}/" gradle.properties
    changes+=("Bump Fabric API v${fabric_api_version}")
    echo >&2 "${changes[-1]}"
fi

./gradlew migrateMappings --mappings "${yarn_mappings_version}"
./gradlew build --refresh-dependencies

if ! git diff --quiet || ! git diff --quiet --staged; then

    mod_version_old="$(cat gradle.properties | grep -E 'mod_version=.+' | cut -d = -f 2)"
    if echo "${changes[@]}" | grep -Fq "Minecraft"; then
        mod_version_new="$(echo "${mod_version_old}" | awk -F. '{printf "%d.%d.0", $1, $2+1}')"
    else
        mod_version_new="$(echo "${mod_version_old}" | awk -F. '{printf "%d.%d.%d", $1, $2, $3+1}')"
    fi
    sed -i "s/^mod_version=.*$/mod_version=${mod_version_new}/" gradle.properties

    git add .
    git --no-pager diff --staged

    printf "\n"
    read -rp "Press enter to COMMIT and PUBLISH the above changes as: v${mod_version_new}"

    git commit -m "v${mod_version_new}" -m "$(printf '%s\n' "${changes[@]}")"
    git push
    git tag "v${mod_version_new}"
    git push origin tag "v${mod_version_new}"

else
    echo >&2 "No changes detected, exiting ..."
fi
