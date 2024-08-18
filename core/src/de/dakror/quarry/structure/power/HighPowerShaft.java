/*******************************************************************************
 * Copyright 2018 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.dakror.quarry.structure.power;

import de.dakror.quarry.game.Item.ItemType;
import de.dakror.quarry.game.Item.Items;
import de.dakror.quarry.game.Layer;
import de.dakror.quarry.game.Science.ScienceType;
import de.dakror.quarry.game.power.PowerNetwork.NetworkStrength;
import de.dakror.quarry.scenes.Game;
import de.dakror.quarry.structure.base.Direction;
import de.dakror.quarry.structure.base.Dock;
import de.dakror.quarry.structure.base.Dock.DockType;
import de.dakror.quarry.structure.base.StructureType;

/**
 * @author Maximilian Stark | Dakror
 */
public class HighPowerShaft extends Substation {
    public static final SubstationSchema classSchema = new SubstationSchema(0, StructureType.HighPowerShaft, 1, 1, 200_000, 200,
            "highpowershaft",
            new Items(ItemType.SteelPlate, 35, ItemType.SteelCable, 100, ItemType.Brick, 50), null,
            1, new Dock(0, 0, Direction.East, DockType.BigPower))
                    .sciences(ScienceType.MineExpansion, ScienceType.Routers, ScienceType.HighPower);

    HighPowerShaft other;

    int direction;

    public HighPowerShaft(int x, int y) {
        this(x, y, classSchema, 1);
    }

    protected HighPowerShaft(int x, int y, SubstationSchema schema, int direction) {
        super(x, y, schema);
        this.direction = direction;
    }

    @Override
    public void onPlacement(boolean fromLoading) {
        super.onPlacement(fromLoading);

        if (!fromLoading && layer != null && direction == 1) {
            Layer otherLayer = Game.G.getLayer(layer.getIndex() + direction);
            if (otherLayer != null) {
                other = new HighPowerShaftBelow(x, y);
                this.other.setUpDirection(upDirection);
                other.other = this;
                otherLayer.addStructure(this.other);
                powerNetwork.getPowerGrid().mergeNetworks(powerNetwork, other.powerNetwork);
                powerNetwork.addEdge(this, 1, other, 1, NetworkStrength.PowerPole);
            }
        }
    }

    @Override
    public void postLoad() {
        super.postLoad();

        other = (HighPowerShaft) Game.G.getLayer(layer.getIndex() + direction).getStructure(x, y);

        if (direction == 1) {
            powerNetwork.getPowerGrid().mergeNetworks(powerNetwork, other.powerNetwork);
            powerNetwork.addEdge(this, 1, other, 1, NetworkStrength.PowerPole);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        other.layer.removeStructure(other);
    }
}