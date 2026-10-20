package com.thestone.mm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.thestone.mm.core.ModDataComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CheckComponentCommand {
    public CheckComponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mm_component").then(Commands.literal("test").executes(this::execute)));

    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.literal("Components : " +    ModDataComponents.DATA_COMPONENT_TYPES.getEntries()), true);
        return 1;
    }
}
