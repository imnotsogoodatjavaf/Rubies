package julians.rubies;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class RubyFoodComponents {
    public static final FoodComponent POWERFUL_POWDER = (new FoodComponent.Builder()).hunger(2).saturationModifier(1.0F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6000, 4), 0.1F).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 6000, 4), 0.1F).snack().alwaysEdible().build();
    public static final FoodComponent RUBY_INFUSED_STEAK = (new FoodComponent.Builder()).hunger(10).saturationModifier(2.5F).build();

}