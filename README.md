# Menu Music

Change the title menu music without much fuzz!

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

[format description](https://minecraft.fandom.com/wiki/Sounds.json#File_structure)

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
