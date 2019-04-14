/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.tiles.machine.tier1;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.registration.RebornRegister;
import reborncore.common.registration.config.ConfigRegistry;
import reborncore.common.util.Inventory;
import techreborn.TechReborn;
import techreborn.init.ModRecipes;
import techreborn.init.TRContent;
import techreborn.init.TRTileEntities;
import techreborn.tiles.TileGenericMachine;

@RebornRegister(TechReborn.MOD_ID)
public class TileAssemblingMachine extends TileGenericMachine implements IContainerProvider {

	@ConfigRegistry(config = "machines", category = "assembling_machine", key = "AssemblingMachineMaxInput", comment = "Assembling Machine Max Input (Value in EU)")
	public static int maxInput = 128;
	@ConfigRegistry(config = "machines", category = "assembling_machine", key = "AssemblingMachineMaxEnergy", comment = "Assembling Machine Max Energy (Value in EU)")
	public static int maxEnergy = 10_000;

	public TileAssemblingMachine() {
		super(TRTileEntities.ASSEMBLY_MACHINE, "AssemblingMachine", maxInput, maxEnergy, TRContent.Machine.ASSEMBLY_MACHINE.block, 3);
		final int[] inputs = new int[] { 0, 1 };
		final int[] outputs = new int[] { 2 };
		this.inventory = new Inventory<>(4, "TileAssemblingMachine", 64, this).withConfiguredAccess();
		this.crafter = new RecipeCrafter(ModRecipes.ASSEMBLING_MACHINE, this, 2, 2, this.inventory, inputs, outputs);
	}
	
	// IContainerProvider
	@Override
	public BuiltContainer createContainer(final EntityPlayer player) {
		return new ContainerBuilder("assemblingmachine").player(player.inventory).inventory().hotbar()
			.addInventory().tile(this).slot(0, 55, 35).slot(1, 55, 55).outputSlot(2, 101, 45).energySlot(3, 8, 72)
			.syncEnergyValue().syncCrafterValue().addInventory().create(this);
	}
}
