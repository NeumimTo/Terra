package com.dfsek.terra.api.command;

import java.util.List;

import com.dfsek.terra.api.command.exception.CommandException;
import com.dfsek.terra.api.command.exception.MalformedCommandException;
import com.dfsek.terra.api.entity.CommandSender;


public interface CommandManager {
    void execute(String command, CommandSender sender, List<String> args) throws CommandException;
    
    void register(String name, Class<? extends CommandTemplate> clazz) throws MalformedCommandException;
    
    List<String> tabComplete(String command, CommandSender sender, List<String> args) throws CommandException;
    
    int getMaxArgumentDepth();
}
