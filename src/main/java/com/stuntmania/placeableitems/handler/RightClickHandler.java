package com.stuntmania.placeableitems.handler;

import static com.stuntmania.placeableitems.init.ModItems.*;
import static net.minecraft.init.Items.*;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.stuntmania.placeableitems.init.ModBlocks;
import com.stuntmania.placeableitems.init.ModItems;
import com.stuntmania.placeableitems.tileentity.TEBowl;
import com.stuntmania.placeableitems.utils.WorldUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RightClickHandler {
	
	/**
	 * List of placeable items
	 */
	private Item[] placeableItems = { ModItems.black_bowl, ModItems.blue_bowl, ModItems.brown_bowl, ModItems.cyan_bowl, ModItems.gray_bowl, ModItems.green_bowl, ModItems.light_blue_bowl, ModItems.light_gray_bowl, ModItems.lime_bowl, ModItems.magenta_bowl, ModItems.orange_bowl, ModItems.pink_bowl, ModItems.purple_bowl, ModItems.red_bowl, ModItems.white_bowl, ModItems.yellow_bowl, apple, beef, bone, book, bowl, bread, brick, bucket, carrot, carrot_on_a_stick, clay_ball, cooked_beef, cooked_fished, cooked_porkchop, cookie, diamond, egg, ender_eye, ender_pearl, fish, glowstone_dust, golden_carrot, gold_ingot, gunpowder, iron_ingot, lava_bucket, melon, milk_bucket, mushroom_stew, netherbrick, paper, porkchop, pumpkin_pie, quartz, record_11, record_13, record_blocks, record_cat, record_chirp, record_far, record_mall, record_mellohi, record_stal, record_strad, record_wait, record_ward, slime_ball, snowball, spider_eye, stick, water_bucket };
	
	private Item[] placeableItemsAirOnly = {};
	
	private Item[] placeableItemsBlockOnly = {};
	
	@SuppressWarnings("incomplete-switch")
	@SubscribeEvent
	public void rightClick(PlayerInteractEvent event) {
		boolean c = event.entityPlayer.capabilities.isCreativeMode;
		ItemStack equip = event.entityPlayer.getCurrentEquippedItem();
		int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
		
		if (!event.world.isRemote)
			switch (event.action) {
			case RIGHT_CLICK_AIR:
				handleRightClickAir(event);
				if (!event.isCanceled())
					break;
				
			case RIGHT_CLICK_BLOCK: {
				handleRightClickBlock(event);
				if (!event.isCanceled())
					break;
				
				/*
				 * Stacking Blocks
				 */
				if (equip != null && event.entityPlayer.isSneaking()) {
					
					// Ingots
					if (event.world.getBlock(event.x, event.y, event.z).equals(ModBlocks.ingot)) {
						if (!event.world.isRemote) {
							if (meta < 6) {
								event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, meta + 2, 3);
								if (!c)
									equip.stackSize--;
							}
						}
						break;
					}
				}
				
				/*
				 * Un-stacking Blocks
				 */
				if (!event.world.isRemote && !event.entityPlayer.isSneaking()) {
					
					// Ingots
					if (event.world.getBlock(event.x, event.y, event.z).equals(ModBlocks.ingot)) {
						if (!c) {
							if (event.world.getBlockMetadata(event.x, event.y, event.z) % 2 == 0) {
								event.world.spawnEntityInWorld(new EntityItem(event.world, event.x, event.y, event.z, new ItemStack(iron_ingot, 1)));
							} else {
								event.world.spawnEntityInWorld(new EntityItem(event.world, event.x, event.y, event.z, new ItemStack(gold_ingot, 1)));
							}
						}
						if (meta > 1) {
							event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, meta - 2, 3);
						}
						if (meta <= 1) {
							event.world.setBlockToAir(event.x, event.y, event.z);
						}
					}
				}
				
				/*
				 * Block Placing
				 */
				if (equip != null && event.entityPlayer.isSneaking() && getBlockFromFace(event.x, event.y, event.z, event.world, event.face).equals(Blocks.air) && !getBlockFromFace(event.x, event.y - 1, event.z, event.world, event.face).equals(Blocks.air)) {
					
					// Ingots
					placeItem(iron_ingot, ModBlocks.ingot, 0, event, equip, c);
					placeItem(gold_ingot, ModBlocks.ingot, 1, event, equip, c);
					
					// Bricks
					placeItem(brick, ModBlocks.brick, 0, event, equip, c);
					placeItem(netherbrick, ModBlocks.brick, 1, event, equip, c);
					
					// Bone
					placeItem(bone, ModBlocks.bone, event, equip, c);
					
					// Book
					placeItem(book, ModBlocks.book, event, equip, c);
					
					// Gunpowder
					placeItem(gunpowder, ModBlocks.gunpowder, event, equip, c);
					
					// Snowball
					placeItem(snowball, ModBlocks.snowball, event, equip, c);
					
					// Ender pearl
					placeItem(ender_pearl, ModBlocks.ender_pearl, event, equip, c);
					
					// Ender eye
					placeItem(ender_eye, ModBlocks.ender_eye, event, equip, c);
					
					// Slime Ball
					placeItem(slime_ball, ModBlocks.slimeBall, event, equip, c);
					
					// Clay ball
					placeItem(clay_ball, ModBlocks.clay, event, equip, c);
					
					// Glowstone dust
					placeItem(glowstone_dust, ModBlocks.glowstone, event, equip, c);
					
					// Stick
					placeItem(stick, ModBlocks.stick, event, equip, c);
					
					// Diamond
					placeItem(diamond, ModBlocks.diamond, event, equip, c);
					
					// Carrot on a stick
					placeItem(carrot_on_a_stick, ModBlocks.carrot_on_stick, event, equip, c);
					
					// Quartz
					placeItem(quartz, ModBlocks.quartz, event, equip, c);
					
					// Paper
					placeItem(paper, ModBlocks.paper, event, equip, c);
					
					// Spider eye
					placeItem(spider_eye, ModBlocks.spiderEye, event, equip, c);
					
					// Buckets
					placeItem(bucket, ModBlocks.bucket, 0, event, equip, c);
					placeItem(water_bucket, ModBlocks.bucket, 1, event, equip, c);
					placeItem(lava_bucket, ModBlocks.bucket, 2, event, equip, c);
					placeItem(milk_bucket, ModBlocks.bucket, 3, event, equip, c);
					
					// Food
					placeItem(apple, ModBlocks.apple, event, equip, c);
					placeItem(melon, ModBlocks.melon, event, equip, c);
					placeItem(egg, ModBlocks.egg, event, equip, c);
					placeItem(pumpkin_pie, ModBlocks.pumpkin_pie, event, equip, c);
					placeItem(chicken, ModBlocks.chicken, event, equip, c);
					placeItem(bread, ModBlocks.bread, event, equip, c);
					placeItem(carrot, ModBlocks.carrot, 0, event, equip, c);
					placeItem(golden_carrot, ModBlocks.carrot, 1, event, equip, c);
					placeItem(porkchop, ModBlocks.porkchop, 0, event, equip, c);
					placeItem(cooked_porkchop, ModBlocks.porkchop, 1, event, equip, c);
					placeItem(beef, ModBlocks.steak, 0, event, equip, c);
					placeItem(cooked_beef, ModBlocks.steak, 1, event, equip, c);
					placeItem(cookie, ModBlocks.cookie, 0, event, equip, c);
					
					// Fish
					/* func_150976_a() == getMetadata */
					if (equip.getItemDamage() == 0) {
						placeItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()).getItem(), ModBlocks.fish, 0, event, equip, c);
						placeItem(new ItemStack(Items.cooked_fished, 1, ItemFishFood.FishType.COD.func_150976_a()).getItem(), ModBlocks.fish, 1, event, equip, c);
					} else if (equip.getItemDamage() == 1) {
						placeItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()).getItem(), ModBlocks.fish, 3, event, equip, c);
						placeItem(new ItemStack(Items.cooked_fished, 1, ItemFishFood.FishType.SALMON.func_150976_a()).getItem(), ModBlocks.fish, 4, event, equip, c);
					}
					
					// Bowls
					if (equip.getItem().getUnlocalizedName().endsWith("Bowl") || equip.getItem().getUnlocalizedName().endsWith("bowl") || equip.getItem().equals(Items.mushroom_stew)) {
						if (placeBlockWithoutMetadata(event.x, event.y, event.z, event.face, ModBlocks.bowl, event.world, event.entityPlayer)) {
							if (equip.getItem().equals(bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(0);
							else if (equip.getItem().equals(black_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(1);
							else if (equip.getItem().equals(red_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(2);
							else if (equip.getItem().equals(green_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(3);
							else if (equip.getItem().equals(brown_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(4);
							else if (equip.getItem().equals(blue_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(5);
							else if (equip.getItem().equals(purple_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(6);
							else if (equip.getItem().equals(cyan_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(7);
							else if (equip.getItem().equals(light_gray_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(8);
							else if (equip.getItem().equals(gray_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(9);
							else if (equip.getItem().equals(pink_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(10);
							else if (equip.getItem().equals(lime_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(11);
							else if (equip.getItem().equals(yellow_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(12);
							else if (equip.getItem().equals(light_blue_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(13);
							else if (equip.getItem().equals(magenta_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(14);
							else if (equip.getItem().equals(orange_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(15);
							else if (equip.getItem().equals(white_bowl))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(16);
							else if (equip.getItem().equals(Items.mushroom_stew))
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(17);
							else
								((TEBowl) getTileEntityFromFace(event.x, event.y, event.z, event.world, event.face)).setState(0);
							if (!c)
								equip.stackSize--;
						}
					}
					
					// Records
					if (equip.getItem().getUnlocalizedName().endsWith("record")) {
						placeItem(record_13, ModBlocks.disk, 0, event, equip, c);
						placeItem(record_cat, ModBlocks.disk, 1, event, equip, c);
						placeItem(record_blocks, ModBlocks.disk, 2, event, equip, c);
						placeItem(record_chirp, ModBlocks.disk, 3, event, equip, c);
						placeItem(record_far, ModBlocks.disk, 4, event, equip, c);
						placeItem(record_mall, ModBlocks.disk, 5, event, equip, c);
						placeItem(record_mellohi, ModBlocks.disk, 6, event, equip, c);
						placeItem(record_stal, ModBlocks.disk, 7, event, equip, c);
						placeItem(record_strad, ModBlocks.disk, 8, event, equip, c);
						placeItem(record_ward, ModBlocks.disk, 9, event, equip, c);
						placeItem(record_11, ModBlocks.disk, 10, event, equip, c);
						placeItem(record_wait, ModBlocks.disk, 11, event, equip, c);
					}
				} // end of != null if
			}// end of case RIGHT_CLICK_BLOCK
			}// end of switch statement
	} // end of rightClick event
	
	private void handleRightClickBlock(PlayerInteractEvent event) {
		if (event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.isSneaking()) {
			for (int i = 0; i < placeableItems.length; i++) {
				if (event.entityPlayer.getCurrentEquippedItem().getItem().equals(placeableItems[i])) {
					event.setCanceled(true);
					return;
				}
			}
			
			for (int i = 0; i < placeableItemsBlockOnly.length; i++) {
				if (event.entityPlayer.getCurrentEquippedItem().getItem().equals(placeableItemsBlockOnly[i])) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
	
	private void handleRightClickAir(PlayerInteractEvent event) {
		if (event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.isSneaking()) {
			for (int i = 0; i < placeableItems.length; i++) {
				if (event.entityPlayer.getCurrentEquippedItem().getItem().equals(placeableItems[i])) {
					event.setCanceled(true);
					return;
				}
			}
			
			for (int i = 0; i < placeableItemsAirOnly.length; i++) {
				if (event.entityPlayer.getCurrentEquippedItem().getItem().equals(placeableItemsAirOnly[i])) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
	
	public static TileEntity getTileEntityFromFace(int x, int y, int z, World world, int face) {
		ForgeDirection direction = ForgeDirection.getOrientation(face);
		return world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
	}
	
	public static Block getBlockFromFace(int x, int y, int z, World world, int face) {
		ForgeDirection direction = ForgeDirection.getOrientation(face);
		return world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
	}
	
	public static boolean placeBlockWithoutMetadata(int x, int y, int z, int face, Block block, World world, EntityPlayer player) {
		return WorldUtils.placeBlockWithoutMetadata(x, y, z, face, block, world, player);
	}
	
	public static boolean placeBlockWithMetadata(int x, int y, int z, int face, Block block, int metadata, World world, EntityPlayer player) {
		return WorldUtils.placeBlockWithMetadata(x, y, z, face, block, metadata, world, player);
	}
	
	private void placeItem(Item item, Block block, int meta, PlayerInteractEvent event, ItemStack equip, boolean c) {
		if (equip.getItem().equals(item))
			if (placeBlockWithMetadata(event.x, event.y, event.z, event.face, block, meta, event.world, event.entityPlayer))
				if (!c)
					equip.stackSize--;
	}
	
	private void placeItem(Item item, Block block, PlayerInteractEvent event, ItemStack equip, boolean c) {
		if (equip.getItem().equals(item))
			if (placeBlockWithoutMetadata(event.x, event.y, event.z, event.face, block, event.world, event.entityPlayer))
				if (!c)
					equip.stackSize--;
	}
}
