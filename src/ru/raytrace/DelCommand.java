package ru.raytrace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DelCommand implements CommandExecutor {
    private final KitRepository kitRepository;

    public DelCommand(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {
        if (params.length == 1) {
            String kitName = params[0];
            return processDelete(kitName, commandSender);
        }

        return false;
    }

    private boolean processDelete(String kitName, CommandSender commandSender) {
        boolean isSuccess = kitRepository.removeByName(kitName);

        if (isSuccess) {
            commandSender.sendMessage("Набор с именем " + kitName + " успешно удален.");
        }

        return isSuccess;
    }

}
