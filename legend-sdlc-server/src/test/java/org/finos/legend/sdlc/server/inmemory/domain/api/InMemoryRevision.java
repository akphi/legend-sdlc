// Copyright 2020 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.sdlc.server.inmemory.domain.api;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.finos.legend.sdlc.domain.model.revision.Revision;
import org.finos.legend.sdlc.server.inmemory.backend.RevisionIdGenerator;

import java.time.Instant;

public class InMemoryRevision implements Revision
{
    private final InMemoryProjectConfiguration configuration;
    private final MutableMap<String, Entity> entities = Maps.mutable.empty();
    private final String revisionId;

    public InMemoryRevision(String context)
    {
        this(context, null);
    }

    public InMemoryRevision(String context, InMemoryRevision parentRevision)
    {
        this.revisionId = RevisionIdGenerator.getInstance().nextRevisionId(context);
        if (parentRevision == null)
        {
            this.configuration = new InMemoryProjectConfiguration();
        }
        else
        {
            this.configuration = parentRevision.configuration;
            this.entities.putAll(parentRevision.entities);
        }
    }

    @Override
    public String getId()
    {
        return this.revisionId;
    }

    @Override
    public String getAuthorName()
    {
        return null;
    }

    @Override
    public Instant getAuthoredTimestamp()
    {
        return null;
    }

    @Override
    public String getCommitterName()
    {
        return null;
    }

    @Override
    public Instant getCommittedTimestamp()
    {
        return null;
    }

    @Override
    public String getMessage()
    {
        return null;
    }

    public boolean containsEntity(Entity entity)
    {
        return this.entities.containsKey(entity.getPath());
    }

    public void addEntity(Entity entity)
    {
        this.entities.put(entity.getPath(), entity);
    }

    public void addEntities(Iterable<? extends Entity> newEntities)
    {
        newEntities.forEach(this::addEntity);
    }

    public void removeEntity(Entity entity)
    {
        this.entities.remove(entity.getPath());
    }

    public void removeEntities(Iterable<? extends Entity> entitiesToRemove)
    {
        entitiesToRemove.forEach(this::removeEntity);
    }

    public Iterable<Entity> getEntities()
    {
        return this.entities.valuesView();
    }

    public InMemoryProjectConfiguration getConfiguration()
    {
        return this.configuration;
    }
}
