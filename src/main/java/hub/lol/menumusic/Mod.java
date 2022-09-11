package hub.lol.menumusic;

import hub.lol.menumusic.mixins.accessors.AbstractSoundInstanceAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Mod implements ModInitializer {
    @Override
    public void onInitialize() {
        Identifier musicId = new Identifier("menumusic", "music");
        SoundEvent musicEvent = new SoundEvent(musicId);
        Registry.register(Registry.SOUND_EVENT, musicId, musicEvent);

        SoundInstance music = PositionedSoundInstance.music(musicEvent);
        ((AbstractSoundInstanceAccessor) music).setRepeat(true);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!(client.currentScreen instanceof TitleScreen)) return;
            if (client.getSoundManager().isPlaying(music)) return;
            client.getSoundManager().play(music);
        });
    }
}
