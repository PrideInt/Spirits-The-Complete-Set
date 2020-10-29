package me.Pride.korra.Spirits.listener;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.Pride.korra.Spirits.combos.Awakening;
import me.Pride.korra.Spirits.combos.Skyrocket;
import me.Pride.korra.Spirits.dark.Corruption;
import me.Pride.korra.Spirits.dark.DarkBeam;
import me.Pride.korra.Spirits.dark.Onslaught;
import me.Pride.korra.Spirits.dark.Shadow;
import me.Pride.korra.Spirits.light.Enlightenment;
import me.Pride.korra.Spirits.light.LightBeam;
import me.Pride.korra.Spirits.light.Safeguard;
import me.Pride.korra.Spirits.light.Wish;
import me.Pride.korra.Spirits.neutral.Float;
import me.Pride.korra.Spirits.passives.Afterglow;
import me.Pride.korra.Spirits.passives.SinisterAura;
import me.Pride.korra.Spirits.passives.WishfulThinking;
import me.Pride.korra.Spirits.util.DarkBeamCharge;
import me.Pride.korra.Spirits.util.LightBeamCharge;
import me.Pride.korra.Spirits.util.RandomChance;
import me.numin.spirits.SpiritElement;
import org.bukkit.inventory.EquipmentSlot;

public class AbilListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onSneak(PlayerToggleSneakEvent event) {

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (bPlayer == null) {
			return;
		}
		final CoreAbility coreAbil = bPlayer.getBoundAbility();
		final String abil = bPlayer.getBoundAbilityName();

		if (coreAbil == null) {
			return;
			
		} else if (abil.equalsIgnoreCase("Wish") && bPlayer.canBend(CoreAbility.getAbility(Wish.class)) && !CoreAbility.hasAbility(player, Wish.class)) {
			new Wish(player);
			
		} else if (abil.equalsIgnoreCase("Enlightenment") && bPlayer.canBend(CoreAbility.getAbility(Enlightenment.class)) && !CoreAbility.hasAbility(player, Enlightenment.class)) {
			new Enlightenment(player);
			
		} else if (abil.equalsIgnoreCase("Corruption")) {
			new Corruption(player);
			
		} else if (abil.equalsIgnoreCase("LightBeam") && bPlayer.canBend(CoreAbility.getAbility(LightBeamCharge.class)) && !CoreAbility.hasAbility(player, LightBeamCharge.class)) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.3F, 0.5F);
			new LightBeamCharge(player);
			
		} else if (abil.equalsIgnoreCase("DarkBeam") && bPlayer.canBend(CoreAbility.getAbility(DarkBeamCharge.class)) && !CoreAbility.hasAbility(player, DarkBeamCharge.class)) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.3F, 0.5F);
			new DarkBeamCharge(player);
			
		} else if (abil.equalsIgnoreCase("Safeguard") && bPlayer.canBend(CoreAbility.getAbility(Safeguard.class)) && !CoreAbility.hasAbility(player, Safeguard.class)) {
			new Safeguard(player);
			
		} else if (abil.equalsIgnoreCase("Shadow") && bPlayer.canBend(CoreAbility.getAbility(Shadow.class)) && !CoreAbility.hasAbility(player, Shadow.class)) {
			new Shadow(player);
			
		}
	}
	
	@SuppressWarnings("deprecation")
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
		}
		final CoreAbility coreAbil = bPlayer.getBoundAbility();
		final String abil = bPlayer.getBoundAbilityName();

		if (coreAbil == null) {
			return;
			
		} else if (abil.equalsIgnoreCase("LightBeam") && bPlayer.canBend(CoreAbility.getAbility(LightBeam.class)) && !CoreAbility.hasAbility(player, LightBeam.class)) {
			LightBeamCharge lightBeam = CoreAbility.getAbility(player, LightBeamCharge.class);
			if (lightBeam != null && lightBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.4F, 1.5F);
				new LightBeam(player);
			}
			
		} else if (abil.equalsIgnoreCase("DarkBeam") && bPlayer.canBend(CoreAbility.getAbility(DarkBeam.class)) && !CoreAbility.hasAbility(player, DarkBeam.class)) {
			DarkBeamCharge darkBeam = CoreAbility.getAbility(player, DarkBeamCharge.class);
			if (darkBeam != null && darkBeam.charged) {
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.4F, 0.5F);
				new DarkBeam(player);
			}
			
		} else if (abil.equalsIgnoreCase("Float") && bPlayer.canBend(CoreAbility.getAbility(Float.class)) && !CoreAbility.hasAbility(player, Float.class)) {
			new Float(player);

		} else if (abil.equalsIgnoreCase("Onslaught") && bPlayer.canBend(CoreAbility.getAbility(Onslaught.class)) && !CoreAbility.hasAbility(player, Onslaught.class)) {
			new Onslaught(player);
			
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent event) {
		
		Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
		Element darkSpirit = SpiritElement.DARK_SPIRIT;
		double darkChance = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Chance");
		double lightChance = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Chance");
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			
			if (bPlayer == null) {
				return;
			}
			
			if (event.getDamage() == 0) {
				event.setCancelled(true);
				return;
			}
			
			if (event.getDamage() >= 1) {
				
				if (bPlayer.hasElement(darkSpirit)) {
					if (event.getEntity() instanceof Player) {
						if (bPlayer.hasElement(SpiritElement.DARK_SPIRIT) && bPlayer.canUsePassive(CoreAbility.getAbility(SinisterAura.class))) {
							if (new RandomChance(darkChance).chanceReached()) {
								new SinisterAura(player);
							}
						}
					}	
				}
				
				if (bPlayer.hasElement(lightSpirit)) {
					if (event.getEntity() instanceof Player) {
						if (bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT) && bPlayer.canUsePassive(CoreAbility.getAbility(WishfulThinking.class))) {
							if (new RandomChance(lightChance).chanceReached()) {
								new WishfulThinking(player);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
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
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof Player)) {
        	return;
        }
		
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			
			if (bPlayer == null) {
				return;
			}
			
			CoreAbility afterglow = CoreAbility.getAbility(Afterglow.class);
			
			Location location = player.getLocation();
			
			if (afterglow != null && bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT) && bPlayer.canUsePassive(afterglow)) {
				new Afterglow(player, location);
			}
		}
	}

	@EventHandler
	public void removeBlocks(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.FALLING_BLOCK) {
			if(Skyrocket.fallingBlocks.remove(event.getEntity())){
				event.setCancelled(true);
			}
		}
	}
}
