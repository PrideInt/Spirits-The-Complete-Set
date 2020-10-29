package me.Pride.korra.Spirits.util;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class LightBeamCharge extends LightAbility implements AddonAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long chargeTime;
	private long cooldown;
	
	private long time;
	public boolean charged;
	
	Random rand = new Random();

	public LightBeamCharge(Player player) {
		super(player);

		chargeTime = config.getLong(path + "ChargeTime");
		cooldown = config.getLong(path + "Cooldown");
		
		time = System.currentTimeMillis();
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Disabled");
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "LightBeam";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}
		if (!player.isOnline() || player.isDead()) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (player.isSneaking()) {
			if (System.currentTimeMillis() > time + chargeTime) {
				charged = true;
				ParticleEffect.SPELL_INSTANT.display(player.getLocation(), 5, 0F, 0.2F, 0F, 0.05F);
			} else {
				ParticleEffect.ENCHANTMENT_TABLE.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.2F);
			}
		} else {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "By channeling all the stored energy within the bodies of "
				+ "Light spirits, they are able to release it in the form of a deadly beam!";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "To use, hold sneak until white spell particles appear and left click.";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
	}

	@Override
	public void stop() {
		super.remove();
	}

}
