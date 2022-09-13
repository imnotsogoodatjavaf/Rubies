package julians.rubies;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class RubyToolMaterial implements ToolMaterial {

    public static final RubyToolMaterial INSTANCE = new RubyToolMaterial();

    @Override
    public int getDurability() {return 4062;}
    @Override
    public float getMiningSpeedMultiplier() {
        return 15.0f;
    }
    @Override
    public float getAttackDamage() {
        return 9.0f;
    }
    @Override
    public int getMiningLevel() {
        return 7;
    }
    @Override
    public int getEnchantability() {return 25;}
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(rubies.RUBY_BLOCK);
    }
}
