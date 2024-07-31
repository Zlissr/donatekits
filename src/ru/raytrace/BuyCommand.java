package ru.raytrace;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class BuyCommand implements CommandExecutor {
    private final String COLOR_CODE = "§a";
    private final SQLHelper sqlHelper;
    private final KitRepository kitRepository;
    private final DiscordHelper discordHelper;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM HH:mm");

    public BuyCommand
            (
                    SQLHelper sqlHelper,
                    KitRepository kitRepository,
                    DiscordHelper discordHelper
            ) {
        this.sqlHelper = sqlHelper;
        this.kitRepository = kitRepository;
        this.discordHelper = discordHelper;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {
        int length = params.length;

        if (length == 1) {
            return processBuy(params[0], commandSender);
        } else if (length == 0) {
            writeListOfAvailableKits(commandSender);
        } else {
            commandSender.sendMessage("Incorrect args!");
            return false;
        }

        return true;
    }

    private void writeListOfAvailableKits(CommandSender commandSender) {
        commandSender.sendMessage(COLOR_CODE + kitRepository.getAvailableKits());
    }

    private boolean processBuy(String kitName, CommandSender commandSender) {
        Optional<Integer> price = kitRepository.getPriceByName(kitName);

        if (!price.isPresent()) {
            commandSender.sendMessage("Не существует набора " + kitName);

            return false;
        }

        boolean isSuccess = sqlHelper.buyKitQuery(
                commandSender.getName(),
                price.get());

        if (isSuccess) {
            processGive(kitName, commandSender);
            sendLogToDiscord(kitName, commandSender);
            commandSender.sendMessage("Вы успешно купили набор " + kitName);
        } else {
            commandSender.sendMessage("Вы не можете позволить себе набор " + kitName);
        }

        return isSuccess;
    }

    private void processGive(String kitName, CommandSender commandSender) {
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                "kit " + kitName + " " + commandSender.getName());
    }

    private void sendLogToDiscord(String kitName, CommandSender commandSender) {
        discordHelper.sendMessage(
                commandSender.getName() + " приобрел набор " + kitName + " в " +
                        DATE_FORMAT.format(new Date(System.currentTimeMillis())));
    }

}