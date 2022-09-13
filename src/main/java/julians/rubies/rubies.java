package julians.rubies;

import com.sun.jna.platform.win32.WinBase;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import julians.rubies.item.CustomAxeItem;
import julians.rubies.item.CustomHoeItem;
import julians.rubies.item.CustomPickaxeItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

import static net.minecraft.world.gen.YOffset.*;

public class rubies implements ModInitializer {

	public static final Item RUBY = new Item(new Item.Settings().group(ItemGroup.MATERIALS).fireproof());
	public static final Item RUBY_INGOT = new Item(new Item.Settings().group(ItemGroup.MATERIALS).fireproof());
	public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.copy(Blocks.NETHERITE_BLOCK));
	public static final Block NETHERITE_INFUSED_RUBY_DIAMOND_BLOCK = new Block(FabricBlockSettings.copy(
			Blocks.OBSIDIAN));
	public static final Item POWERFUL_POWDER = new Item(new Item.Settings().group(ItemGroup.FOOD).food(
			RubyFoodComponents.POWERFUL_POWDER).fireproof());
	public static final Item RUBY_INFUSED_STEAK = new Item(new Item.Settings().group(ItemGroup.FOOD).food(
			RubyFoodComponents.RUBY_INFUSED_STEAK).fireproof());
	public static final Block DEEPSLATE_RUBY_ORE = new Block(FabricBlockSettings.copy(Blocks.DEEPSLATE));
	public static final Item RUBY_SHARD = new Item(new Item.Settings().group(ItemGroup.MISC).fireproof());
	public static final Item NETHERITE_SHARD = new Item(new Item.Settings().group(ItemGroup.MISC).fireproof());
	private static ConfiguredFeature<?, ?> RUBY_ORE_OVERWORLD = new ConfiguredFeature(Feature.ORE,
			new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, rubies.DEEPSLATE_RUBY_ORE.getDefaultState(),
			4));
	public static PlacedFeature RUBY_ORE_OVERWORLD_PLACED = new PlacedFeature(
			RegistryEntry.of(RUBY_ORE_OVERWORLD), Arrays.asList(
			CountPlacementModifier.of(1), // number of veins per chunk
			SquarePlacementModifier.of(),
			HeightRangePlacementModifier.uniform(getBottom(), fixed(59))
	));

	private static final Identifier WARDEN_LOOT_TABLE = EntityType.WARDEN.getLootTableId();
	private static final Identifier DRAGON_LOOT_TABLE = EntityType.ENDER_DRAGON.getLootTableId();

	public static ToolItem RUBY_SWORD = new SwordItem(RubyToolMaterial.INSTANCE, 3, -2.4F, new
			Item.Settings().group(ItemGroup.COMBAT).fireproof());
	public static ToolItem RUBY_AXE = new CustomAxeItem(RubyToolMaterial.INSTANCE, 7, -3.2F,
			new Item.Settings().group(ItemGroup.TOOLS).fireproof());
	public static ToolItem RUBY_PICKAXE = new CustomPickaxeItem(RubyToolMaterial.INSTANCE, 1,
			-2.8f, new Item.Settings().group(ItemGroup.TOOLS).fireproof());
	public static ToolItem RUBY_SHOVEL = new ShovelItem(RubyToolMaterial.INSTANCE, 1.5F, -3.0F,
			new Item.Settings().group(ItemGroup.TOOLS).fireproof());
	public static ToolItem RUBY_HOE = new CustomHoeItem(RubyToolMaterial.INSTANCE, 0, -3.2F, new
			Item.Settings().group(ItemGroup.TOOLS).fireproof());
	public static Enchantment ENSNARE = new Ensnare();
	public static Enchantment DESTRENGTH = new DeStrength();
	public static Enchantment VENOM = new Venom();
	public static Enchantment FATIGUE = new Fatigue();
	public static Enchantment FLOATING = new Floating();

	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby"), RUBY);

		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_ingot"), RUBY_INGOT);

		Registry.register(Registry.BLOCK, new Identifier("rubies", "ruby_block"), RUBY_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_block"), new BlockItem(
				RUBY_BLOCK, new FabricItemSettings().group(ItemGroup.MATERIALS).fireproof()));

		Registry.register(Registry.BLOCK, new Identifier("rubies",
				"netherite_infused_ruby_diamond_block"), NETHERITE_INFUSED_RUBY_DIAMOND_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("rubies",
				"netherite_infused_ruby_diamond_block"), new BlockItem(NETHERITE_INFUSED_RUBY_DIAMOND_BLOCK,
				new FabricItemSettings().group(ItemGroup.MATERIALS).fireproof()));

		Registry.register(Registry.ITEM, new Identifier("rubies", "powerful_powder"), POWERFUL_POWDER);

		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_infused_steak"),
				RUBY_INFUSED_STEAK);

		Registry.register(Registry.BLOCK, new Identifier("rubies", "deepslate_ruby_ore"),
				DEEPSLATE_RUBY_ORE);
		Registry.register(Registry.ITEM, new Identifier("rubies", "deepslate_ruby_ore"), new
				BlockItem(DEEPSLATE_RUBY_ORE, new Item.Settings().group(ItemGroup.MATERIALS).fireproof()));

		Registry.register(Registry.ITEM, new Identifier("rubies","ruby_shard"), RUBY_SHARD);

		Registry.register(Registry.ITEM, new Identifier("rubies", "netherite_shard"), NETHERITE_SHARD);

		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
				new Identifier("rubies", "overworld_ruby_ore"), RUBY_ORE_OVERWORLD);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("rubies",
						"overworld_ruby_ore"), RUBY_ORE_OVERWORLD_PLACED);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("rubies",
						"overworld_ruby_ore")));

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && WARDEN_LOOT_TABLE.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(rubies.RUBY));

				tableBuilder.pool(poolBuilder);
			}

			if (source.isBuiltin() && DRAGON_LOOT_TABLE.equals(id)) {
				LootPool.Builder poolbuilder1 = LootPool.builder()
						.with(ItemEntry.builder(rubies.NETHERITE_SHARD));

				tableBuilder.pool(poolbuilder1);
			}
		});

		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_sword"), RUBY_SWORD);
		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_axe"), RUBY_AXE);
		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_pickaxe"), RUBY_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("rubies", "ruby_shovel"), RUBY_SHOVEL);
		Registry.register(Registry.ITEM, new Identifier("rubies","ruby_hoe"), RUBY_HOE);

		Registry.register(Registry.ENCHANTMENT, new Identifier("rubies", "ensnare"), ENSNARE);
		Registry.register(Registry.ENCHANTMENT, new Identifier("rubies", "destrength"), DESTRENGTH);
		Registry.register(Registry.ENCHANTMENT, new Identifier("rubies", "venom"), VENOM);
		Registry.register(Registry.ENCHANTMENT, new Identifier("rubies", "fatigue"), FATIGUE);
		Registry.register(Registry.ENCHANTMENT, new Identifier("rubies", "floating"), FLOATING);

	}
}