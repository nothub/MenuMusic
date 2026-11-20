# Menu Music

Fabric & NeoForge mod. Change the title menu music without much fuss!

[![GitHub](https://img.shields.io/github/downloads/nothub/menumusic/total?style=flat&label=GitHub&logo=github&color=181717)](https://github.com/nothub/menumusic/releases)
[![Modrinth](https://img.shields.io/modrinth/dt/To1L7Q0X?style=flat&label=Modrinth&logo=modrinth&color=1bd96a)](https://modrinth.com/mod/menumusic)
[![CurseForge](https://img.shields.io/curseforge/dt/1113116?style=flat&label=CurseForge&logo=curseforge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/menumusic)

---

## Usage

1. Install the mod.
2. Load a sound resource with `menumusic:music` identifier.
3. Done, enjoy the music :)

## Sound resources

### Resource pack

[example packs](https://github.com/nothub/menumusic/releases)

```
menumusic.zip
├── assets
│   └── menumusic
│       ├── sounds
│       │   ├── track1.ogg
│       │   └── track2.ogg
│       └── sounds.json
└── pack.mcmeta
```

### KubeJS

[loading assets](https://mods.latvian.dev/books/kubejs/page/loading-assets-and-data)

```
kubejs/
└── assets
    └── menumusic
        ├── sounds
        │   ├── track1.ogg
        │   └── track2.ogg
        └── sounds.json
```

### sounds.json

[format description](https://minecraft.wiki/w/Sounds.json#File_structure)

```json
{
  "music": {
    "category": "music",
    "sounds": [
      "menumusic:track1",
      "menumusic:track2"
    ]
  }
}
```
