package hub.lol.menumusic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Mod implements ModInitializer {
    public static final Identifier MUSIC_ID = new Identifier("menumusic", "music");
    public static final SoundEvent MUSIC_EVENT = new SoundEvent(MUSIC_ID);
    public static final SoundInstance MUSIC = new PositionedSoundInstance(Mod.MUSIC_EVENT.getId(), SoundCategory.MUSIC, 1.0F, 1.0F, true, 0, SoundInstance.AttenuationType.NONE, 0.0, 0.0, 0.0, true); // PositionedSoundInstance.music(Mod.MUSIC_EVENT);

    @Override
    public void onInitialize() {
        Registry.register(Registry.SOUND_EVENT, MUSIC_ID, MUSIC_EVENT);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!(client.currentScreen instanceof TitleScreen)) return;
            if (client.getSoundManager().isPlaying(MUSIC)) return;
            client.getSoundManager().play(MUSIC);
        });
    }
}
