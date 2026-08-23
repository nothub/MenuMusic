package hub.lol.menumusic;

import hub.lol.menumusic.mixins.accessors.AbstractSoundInstanceAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class MenuMusicCore {

    private final SoundInstance music;

    public MenuMusicCore() {
        Identifier musicId = Identifier.fromNamespaceAndPath("menumusic", "music");
        SoundEvent musicEvent = SoundEvent.createVariableRangeEvent(musicId);

        music = SimpleSoundInstance.forMusic(musicEvent);
        ((AbstractSoundInstanceAccessor) music).setRepeat(true);
    }

    public void onTick(Minecraft mc) {
        if (!(mc.gui.screen() instanceof TitleScreen)) return;
        if (mc.getSoundManager().isActive(music)) return;
        mc.getSoundManager().play(music);
    }

}
