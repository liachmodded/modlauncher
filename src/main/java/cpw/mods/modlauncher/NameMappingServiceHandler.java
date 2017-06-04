/*
 * Modlauncher - utility to launch Minecraft-like game environments with runtime transformation
 * Copyright ©2016-2017 cpw and others
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package cpw.mods.modlauncher;

import cpw.mods.modlauncher.api.INameMappingService;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cpw.mods.modlauncher.Logging.launcherLog;

/**
 * Allow names to be transformed between naming domains.
 */
class NameMappingServiceHandler
{
    private final ServiceLoader<INameMappingService> namingServices;
    private final Map<String, NameMappingServiceDecorator> namingLookup;

    public NameMappingServiceHandler()
    {
        namingServices = ServiceLoader.load(INameMappingService.class);
        launcherLog.info("Found naming services {}", () -> ServiceLoaderStreamUtils.toList(namingServices));
        namingLookup = StreamSupport.stream(namingServices.spliterator(), false)
                .collect(Collectors.toMap(INameMappingService::mappingName, NameMappingServiceDecorator::new));
    }
}