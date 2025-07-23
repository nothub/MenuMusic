package hub.lol.menumusic.mixins;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.sound.SoundEvents.MUSIC_MENU;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Shadow
    @Final
    private SoundSystem soundSystem;

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)Lnet/minecraft/client/sound/SoundSystem$PlayResult;", cancellable = true)
    private void injectPlayDirect(SoundInstance sound, CallbackInfoReturnable<SoundSystem.PlayResult> ci) {
        if (sound == null) {
            return;
        }
        if (sound.getId().equals(MUSIC_MENU.registryKey().getValue())) {
            ci.setReturnValue(SoundSystem.PlayResult.NOT_STARTED);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", cancellable = true)
    private void injectPlayDelayed(SoundInstance sound, int delay, CallbackInfo ci) {
        if (sound == null) {
            return;
        }
        if (sound.getId().equals(MUSIC_MENU.registryKey().getValue())) {
            ci.cancel();
        }
    }
}
