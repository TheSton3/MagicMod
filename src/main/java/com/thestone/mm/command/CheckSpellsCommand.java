package com.thestone.mm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.thestone.mm.core.ModSpells;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CheckSpellsCommand {
    public CheckSpellsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mm_spell").then(Commands.literal("test").executes(this::execute)));

    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {


        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.literal("Spells : " + ModSpells.SPELLS.getEntries()), true);
        return 1;
    }
}
