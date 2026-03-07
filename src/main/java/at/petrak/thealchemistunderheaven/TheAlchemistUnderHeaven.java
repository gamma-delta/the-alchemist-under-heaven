package at.petrak.thealchemistunderheaven;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheAlchemistUnderHeaven implements ModInitializer {
  public static final String MOD_ID = "the-alchemist-under-heaven";

  // This logger is used to write text to the console and the log file.
  // It is considered best practice to use your mod id as the logger's name.
  // That way, it's clear which mod wrote info, warnings, and errors.
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    LOGGER.info("Hello from The Alchemist Under Heaven");

    ModBlocks.init();
    ModItems.init();
    ModBlockEntities.init();
  }

  public static Identifier modLoc(String s) {
    return Identifier.fromNamespaceAndPath(MOD_ID, s);
  }
}