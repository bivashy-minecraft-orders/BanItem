/*
 * BanItem - Lightweight, powerful & configurable per world ban item plugin
 * Copyright (C) 2021 André Sustac
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your action) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.andross.banitem.utils.scanners;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import fr.andross.banitem.BanItem;
import fr.andross.banitem.BanUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * A simple async scanner to check if players wears a banned item
 *
 * @author Andross
 * @version 3.1.1
 */
public final class WearScanner {

    private final BanItem plugin;
    private final BanUtils utils;
    private boolean enabled;
    private WrappedTask task;

    public WearScanner(@NotNull final BanItem plugin, @NotNull final BanUtils utils) {
        this.plugin = plugin;
        this.utils = utils;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            if (task == null)
                task = plugin.getFoliaLib()
                        .getScheduler()
                        .runTimerAsync(() -> Bukkit.getOnlinePlayers().forEach(utils::checkPlayerArmors), 800, 800, TimeUnit.MILLISECONDS);
        } else {
            if (task != null) {
                task.cancel();
                task = null;
            }
        }
    }

}
