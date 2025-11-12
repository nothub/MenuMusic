#!/usr/bin/env bash

set -euo pipefail

# go to project root
cd "$(dirname "$(realpath "$0")")/.."

minecraft_version="$(curl -fsSL "https://launchermeta.mojang.com/mc/game/version_manifest.json" \
    | jq -r '.versions | map(select(.type == "release")) | .[0].id')"

yarn_mappings_version="$(curl -fsSL "https://meta.fabricmc.net/v2/versions/yarn/${minecraft_version}" \
    | jq -r '[.[] | select(.stable==true)][0].version // .[0].version')"

fabric_loader_version="$(curl -fsSL "https://meta.fabricmc.net/v2/versions/loader/${minecraft_version}" \
    | jq -r '[.[] | select(.loader.stable==true)][0].loader.version // .[0].loader.version')"

fabric_api_version="$(curl -fsSL --get "https://api.modrinth.com/v2/project/fabric-api/version" \
    --data-urlencode 'loaders=["fabric"]' \
    --data-urlencode "game_versions=[\"${minecraft_version}\"]" \
    --data-urlencode 'featured=true' \
    | jq -r '.[0].version_number')"

sed -i "s/^minecraft_version=.*$/minecraft_version=${minecraft_version}/" gradle.properties
sed -i "s/^yarn_mappings_version=.*$/yarn_mappings_version=${yarn_mappings_version}/" gradle.properties
sed -i "s/^fabric_loader_version=.*$/fabric_loader_version=${fabric_loader_version}/" gradle.properties
sed -i "s/^fabric_api_version=.*$/fabric_api_version=${fabric_api_version}/" gradle.properties

./gradlew clean
./gradlew migrateMappings --mappings "${yarn_mappings_version}"
./gradlew build --refresh-dependencies

if test -n "$(git status -s > /dev/null)"; then
    mod_version_old="$(cat gradle.properties | grep -E 'mod_version=.+' | cut -d = -f 2)"
    mod_version_new=$(echo "${mod_version_old}" | awk -F. '{printf "%d.%d.0", $1, $2+1}')
    git add .
    git commit -m "v${mod_version_new}"
    git push
    git tag "v${mod_version_new}"
    git push origin tag "v${mod_version_new}"
fi
