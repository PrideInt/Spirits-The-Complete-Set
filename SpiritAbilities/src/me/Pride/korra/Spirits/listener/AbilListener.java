package me.Pride.korra.Spirits.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.Pride.korra.Spirits.dark.Corruption;
import me.Pride.korra.Spirits.dark.DarkBeam;
import me.Pride.korra.Spirits.dark.Onslaught;
import me.Pride.korra.Spirits.light.Enlightenment;
import me.Pride.korra.Spirits.light.LightBeam;
import me.Pride.korra.Spirits.light.Wish;
import me.Pride.korra.Spirits.neutral.Float;
import me.Pride.korra.Spirits.passives.SinisterAura;
import me.Pride.korra.Spirits.passives.WishfulThinking;
import me.Pride.korra.Spirits.util.DarkBeamCharge;
import me.Pride.korra.Spirits.util.LightBeamCharge;
import me.xnuminousx.spirits.elements.SpiritElement;
import org.bukkit.inventory.EquipmentSlot;

public class AbilListener implements Listener {
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (event.isCancelled() || bPlayer == null) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Wish")) {
			new Wish(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Enlightenment")) {
			new Enlightenment(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Corruption")) {
			new Corruption(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightBeam")) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.05F, 0.5F);
			new LightBeamCharge(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkBeam")) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.05F, 0.5F);
			new DarkBeamCharge(player);
			
		}
	}
	
	@EventHandler
	public void click(final PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) {
			return;
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.isCancelled()){
			return;
		}

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (bPlayer == null) {
			return;
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("LightBeam")) {
			LightBeamCharge lightBeam = CoreAbility.getAbility(player, LightBeamCharge.class);
			if (lightBeam != null && lightBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.4F, 1.5F);
				new LightBeam(player);
			}
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("DarkBeam")) {
			DarkBeamCharge darkBeam = CoreAbility.getAbility(player, DarkBeamCharge.class);
			if (darkBeam != null && darkBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.4F, 0.5F);
				new DarkBeam(player);
			}
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Float")) {
			new Float(player);

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Onslaught")) {
			new Onslaught(player);
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		
		Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
		Element darkSpirit = SpiritElement.DARK_SPIRIT;
		double chance = Math.random();
		double darkChance = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Chance");
		double lightChance = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Chance");
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			
			if (event.isCancelled() || bPlayer == null) {
				return;
			}
			
			if (event.getDamage() == 0) {
				return;
				
			} else if (event.getDamage() >= 1) {
				
				if (bPlayer.hasElement(darkSpirit)) {
					if (event.getEntity() instanceof Player) {
						if (chance <= darkChance) {
							new SinisterAura(player);
						}
					}	
				}
				
				if (bPlayer.hasElement(lightSpirit)) {
					if (event.getEntity() instanceof Player) {
						if (chance <= lightChance) {
							new WishfulThinking(player);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player)) {
        	return;
        }
        
        if (event.getTarget() instanceof Player) {
        	Player player = (Player) event.getTarget();
        	BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            
        	boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Passives.Dark.DarkAlliance.Enabled");
        	
        	if (enabled) {
        		if (bPlayer.hasElement(SpiritElement.DARK_SPIRIT)) {
            		event.setCancelled(true);
                    event.setTarget(null);
            	}
        	}
        }
    }
}
