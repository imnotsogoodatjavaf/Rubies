package julians.rubies;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class Floating extends Enchantment {
    public Floating() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
            return 300;
        }

        @Override
        public int getMaxLevel() {
            return 10;
        }

        public void onTargetDamaged(LivingEntity user, Entity target, int level) {
            if(target instanceof LivingEntity) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200 * level,
                        1));
            }

            super.onTargetDamaged(user, target, level);
        }

        @Override
        public boolean isTreasure() {
            return true;
        }

        public boolean isAcceptableItem(ItemStack stack) {
            return stack.getItem() instanceof AxeItem ? true : super.isAcceptableItem(stack);
        }
    }
