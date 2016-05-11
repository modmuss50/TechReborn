package techreborn.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import reborncore.RebornCore;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.config.ConfigTechReborn;

import java.util.List;

public class ItemFrequencyTransmitter extends ItemTextureBase implements ITexturedItem
{

	public ItemFrequencyTransmitter()
	{
		setUnlocalizedName("techreborn.frequencyTransmitter");
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		RebornCore.jsonDestroyer.registerObject(this);
	}

	@Override public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("x", pos.getX());
		stack.getTagCompound().setInteger("y", pos.getY());
		stack.getTagCompound().setInteger("z", pos.getZ());
		stack.getTagCompound().setInteger("dim", world.provider.getDimension());

		if (!world.isRemote && ConfigTechReborn.FreqTransmitterChat)
		{
			player.addChatMessage(new TextComponentString(
							ChatFormatting.GRAY + "Set to X: " +
							ChatFormatting.GOLD + pos.getX() +
							ChatFormatting.GRAY + " Y: " +
							ChatFormatting.GOLD + pos.getY() +
							ChatFormatting.GRAY + " Z: " +
							ChatFormatting.GOLD + pos.getZ() +
							ChatFormatting.GRAY + " in " +
							ChatFormatting.GOLD + DimensionManager.getProviderType(world.provider.getDimension()).getName() + " ("+world.provider.getDimension()+")"));
		}
		return EnumActionResult.SUCCESS;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if(ConfigTechReborn.FreqTransmitterTooltip)
		{
			if (stack.getTagCompound() != null)
			{
				int x = stack.getTagCompound().getInteger("x");
				int y = stack.getTagCompound().getInteger("y");
				int z = stack.getTagCompound().getInteger("z");
				int dim = stack.getTagCompound().getInteger("dim");

				list.add("X: " + x);
				list.add("Y: " + y);
				list.add("X: " + z);
				list.add(ChatFormatting.DARK_GRAY + DimensionManager.getProviderType(dim).getName());

			} else
			{
				list.add(ChatFormatting.GRAY + "No Coordinates Set");
			}
		}
	}

	@Override public int getMaxMeta()
	{
		return 1;
	}

	@Override public String getTextureName(int arg0)
	{
		return "techreborn:items/misc/frequency_transmitter";
	}
}
