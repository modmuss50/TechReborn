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

package techreborn.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import reborncore.api.IListInfoProvider;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.client.containerBuilder.builder.BuiltContainer;
import reborncore.client.containerBuilder.builder.ContainerBuilder;
import reborncore.common.recipes.RecipeCrafter;
import reborncore.common.registration.RebornRegister;
import reborncore.common.registration.config.ConfigRegistry;
import reborncore.common.util.Inventory;
import reborncore.common.util.ItemUtils;
import techreborn.TechReborn;
import techreborn.init.ModRecipes;
import techreborn.init.TRContent;
import techreborn.init.TRTileEntities;
import techreborn.items.DynamicCell;

import java.util.List;

@RebornRegister(TechReborn.MOD_ID)
public class TileIndustrialCentrifuge extends TileGenericMachine implements IContainerProvider, IListInfoProvider {

	@ConfigRegistry(config = "machines", category = "centrifuge", key = "CentrifugeMaxInput", comment = "Centrifuge Max Input (Value in EU)")
	public static int maxInput = 32;
	@ConfigRegistry(config = "machines", category = "centrifuge", key = "CentrifugeMaxEnergy", comment = "Centrifuge Max Energy (Value in EU)")
	public static int maxEnergy = 10_000;

	public TileIndustrialCentrifuge() {
		super(TRTileEntities.INDUSTRIAL_CENTRIFUGE, "IndustrialCentrifuge", maxInput, maxEnergy, TRContent.Machine.INDUSTRIAL_CENTRIFUGE.block, 6);
		final int[] inputs = new int[] { 0, 1 };
		final int[] outputs = new int[] { 2, 3, 4, 5 };
		this.inventory = new Inventory<>(7, "TileIndustrialCentrifuge", 64, this).withConfiguredAccess();
		this.crafter = new RecipeCrafter(ModRecipes.CENTRIFUGE, this, 2, 4, this.inventory, inputs, outputs);
	}
	
	// IContainerProvider
	@Override
	public BuiltContainer createContainer(final EntityPlayer player) {
		return new ContainerBuilder("centrifuge").player(player.inventory).inventory().hotbar()
			.addInventory().tile(this)
			.filterSlot(1, 40, 54, stack -> ItemUtils.isItemEqual(stack, DynamicCell.getEmptyCell(1), true, true))
			.filterSlot(0, 40, 34, stack -> !ItemUtils.isItemEqual(stack, DynamicCell.getEmptyCell(1), true, true))
			.outputSlot(2, 82, 44).outputSlot(3, 101, 25)
			.outputSlot(4, 120, 44).outputSlot(5, 101, 63).energySlot(6, 8, 72).syncEnergyValue()
			.syncCrafterValue().addInventory().create(this);
	}
	
	// IListInfoProvider
	@Override
	public void addInfo(final List<ITextComponent> info, final boolean isRealTile, boolean hasData) {
		super.addInfo(info, isRealTile, hasData);
		info.add(new TextComponentString("Round and round it goes"));
	}
}