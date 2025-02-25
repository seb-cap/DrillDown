package de.dakror.quarry.structure.producer;

import de.dakror.quarry.Const;

/*******************************************************************************
 * Copyright 2017 Maximilian Stark | Dakror <mail@dakror.de>
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

import de.dakror.quarry.game.Item.ItemType;
import de.dakror.quarry.game.Item.Items;
import de.dakror.quarry.game.Item.Items.Amount;
import de.dakror.quarry.structure.base.Direction;
import de.dakror.quarry.structure.base.Dock;
import de.dakror.quarry.structure.base.Dock.DockFilter;
import de.dakror.quarry.structure.base.Dock.DockType;
import de.dakror.quarry.structure.base.ProducerStructure;
import de.dakror.quarry.structure.base.RecipeList;
import de.dakror.quarry.structure.base.Schema.Flags;
import de.dakror.quarry.structure.base.StructureType;
import de.dakror.quarry.util.Sfx;

/**
 * @author Maximilian Stark | Dakror
 */
public class Mason extends ProducerStructure {
    public static final ProducerSchema classSchema = new ProducerSchema(0,
            StructureType.Mason, 2, 2,
            "mason",
            new Items(ItemType.Stone, 5, ItemType.Wood, 15, ItemType.Scaffolding, 15, ItemType.IronIngot, 10),
            new RecipeList() {
                @Override
                protected void init() {
                    add(new Recipe(15f, "stone")
                            .input(new Amount(ItemType.Stone, 1))
                            .output(new Amount(ItemType.StoneBrick, 1), new Amount(ItemType.StoneGravel, 1)));
                }
            }, new Sfx("mason" + Const.SFX_FORMAT),
            true,
            new Dock(1, 0, Direction.South, DockType.ItemOut),
            new Dock(1, 0, Direction.East, DockType.ItemOut), new Dock(0, 1, Direction.North, DockType.ItemIn, new DockFilter(ItemType.Stone)))
                    .flags(Flags.TextureAlwaysUpright);

    public Mason(int x, int y) {
        super(x, y, classSchema);
    }
}
