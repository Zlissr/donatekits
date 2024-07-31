package ru.raytrace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddCommand implements CommandExecutor {
    private final KitRepository kitRepository;

    public AddCommand(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {
        boolean isSuccess = false;
        String errorMessage = "Incorrect args!";

        if (params.length == 2) {
            if (isValid(params)) {
                String kitName = params[0];
                int price = Integer.parseInt(params[1]);
                addEntryToKitMap(kitName, price, commandSender);
                isSuccess = true;
            } else {
                errorMessage = "Пишите по-человечески";
            }
        }

        if (!isSuccess) {
            commandSender.sendMessage(errorMessage);
        }

        return isSuccess;
    }

    private boolean isValid(String[] params) {
        return params[0].matches("^[a-zA-Z0-9]+$") && params[1].matches("^\\d+$");
    }

    private void addEntryToKitMap(String kitName, int price, CommandSender commandSender) {
        kitRepository.addNewEntry(kitName, price);
        commandSender.sendMessage("Набор успешно добавлен!");
    }

}
