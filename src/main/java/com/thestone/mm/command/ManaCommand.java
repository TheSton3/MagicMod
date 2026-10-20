package com.thestone.mm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.thestone.mm.core.ModAttachmentTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ManaCommand {

    public ManaCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mana").then(Commands.literal("test").executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {

        ServerPlayer player = commandSourceStackCommandContext.getSource().getPlayer();
        int mana = player.getData(ModAttachmentTypes.MANA);

        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.literal("Current Mana : " + mana), true);
        return 1;
    }
}
