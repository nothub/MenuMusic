# Menu Music

Change the title menu music without much fuzz!

---

## Usage

1. Install the mod.
2. Activate a resource pack that provides sound resources with `menumusic:music` identifier.
3. Done, enjoy the music :)

## Resource pack

[example archive](https://github.com/nothub/menumusic/releases/latest/download/menumusic.zip)

### Structure

resourcepack.zip

```
├── assets
│   └── menumusic
│       ├── sounds
│       │   ├── track1.ogg
│       │   └── track2.ogg
│       └── sounds.json
└── pack.mcmeta
```

sounds.json

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
