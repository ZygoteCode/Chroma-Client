package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.tileentity.TileEntityChest;

public final class WTileEntity {
    public static boolean isTrappedChest(TileEntityChest chest) {
        return chest.getChestType() == 1;
    }
}

