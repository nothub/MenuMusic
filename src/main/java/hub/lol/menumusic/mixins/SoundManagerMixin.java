package hub.lol.menumusic.mixins;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.sound.SoundEvents.MUSIC_MENU;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    private void injectPlayDirect(SoundInstance sound, CallbackInfo ci) {
        if (sound.getId().equals(MUSIC_MENU.getId())) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", cancellable = true)
    private void injectPlayDelayed(SoundInstance sound, int delay, CallbackInfo ci) {
        if (sound.getId().equals(MUSIC_MENU.getId())) ci.cancel();
    }
}
