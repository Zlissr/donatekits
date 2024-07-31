package ru.raytrace;

import org.bukkit.plugin.java.JavaPlugin;

//TODO: Config for Database INFO, webhook url
public class Main extends JavaPlugin {
    private final KitRepository kitRepository = new KitRepository("kitsData.bin");
    private final DiscordHelper discordHelper = new DiscordHelper("yourWebhookUrl");
    private final SQLHelper sqlHelper = new SQLHelper
            (
                    "yourUrl",
                    "yourUserName",
                    "yourPass"
            );

    public void onEnable() {
        this.getCommand("addkit").setExecutor(new AddCommand(kitRepository));
        this.getCommand("buykit").setExecutor(new BuyCommand(sqlHelper, kitRepository, discordHelper));
        this.getCommand("delkit").setExecutor(new DelCommand(kitRepository));
        System.out.println("Donate kits successful initialized.");
    }

    public void onDisable() {
        kitRepository.serialize();
    }

}