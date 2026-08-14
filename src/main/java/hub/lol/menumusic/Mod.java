package hub.lol.menumusic;

import hub.lol.menumusic.mixins.accessors.AbstractSoundInstanceAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class Mod implements ModInitializer {
    @Override
    public void onInitialize() {
        Identifier musicId = Identifier.fromNamespaceAndPath("menumusic", "music");
        SoundEvent musicEvent = SoundEvent.createVariableRangeEvent(musicId);
        Registry.register(BuiltInRegistries.SOUND_EVENT, musicId, musicEvent);

        SoundInstance music = SimpleSoundInstance.forMusic(musicEvent);
        ((AbstractSoundInstanceAccessor) music).setRepeat(true);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!(client.screen instanceof TitleScreen)) return;
            if (client.getSoundManager().isActive(music)) return;
            client.getSoundManager().play(music);
        });
    }
}
