package lol.hub.menumusic.fabric;

import lol.hub.menumusic.MenuMusicCore;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Main implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        var core = new MenuMusicCore();
        ClientTickEvents.END_CLIENT_TICK.register(core::onTick);
    }

}
