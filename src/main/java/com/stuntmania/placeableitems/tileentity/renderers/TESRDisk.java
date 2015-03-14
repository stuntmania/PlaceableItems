package com.stuntmania.placeableitems.tileentity.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.stuntmania.placeableitems.PlaceableItems;
import com.stuntmania.placeableitems.tileentity.TEApple;
import com.stuntmania.placeableitems.tileentity.TEDisk;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TESRDisk extends TileEntitySpecialRenderer {
	
	IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(PlaceableItems.MODID, "obj/disk.obj"));
	ResourceLocation disk13 = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_13.png");
	ResourceLocation diskCat = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_cat.png");
	ResourceLocation diskBlocks = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_blocks.png");
	ResourceLocation diskChirp = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_chirp.png");
	ResourceLocation diskFar = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_far.png");
	ResourceLocation diskMall = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_mall.png");
	ResourceLocation diskMellohi = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_mellohi.png");
	ResourceLocation diskStal = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_stal.png");
	ResourceLocation diskStrad = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_strad.png");
	ResourceLocation diskWard = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_ward.png");
	ResourceLocation disk11 = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_11.png");
	ResourceLocation diskWait = new ResourceLocation(PlaceableItems.MODID, "textures/blocks/disks/disk_wait.png");

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float p_147500_8_) {
		TEDisk facedEntity = (TEDisk) entity;
		
		switch(facedEntity.getState()) {
		case 0:
			bindTexture(disk13);
			break;
		case 1:
			bindTexture(diskCat);
			break;
		case 2:
			bindTexture(diskBlocks);
			break;
		case 3:
			bindTexture(diskChirp);
			break;
		case 4:
			bindTexture(diskFar);
			break;
		case 5:
			bindTexture(diskMall);
			break;
		case 6:
			bindTexture(diskMellohi);
			break;
		case 7:
			bindTexture(diskStal);
			break;
		case 8:
			bindTexture(diskStrad);
			break;
		case 9:
			bindTexture(diskWard);
			break;
		case 10:
			bindTexture(disk11);
			break;
		case 11:
			bindTexture(diskWait);
			break;
		}
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.0F, (float) z + 0.5F);
		GL11.glScalef(1, 1, 1);
		
		int facing = facedEntity.getFacing();
		int k = 0;
		k = facing * 90;
		GL11.glRotatef(k, 0.0F, 1.0F, 0.0F);
		
		model.renderAll();
		GL11.glPopMatrix();
	}
}
