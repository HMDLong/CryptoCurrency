package com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback

import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.HistoryEntry

interface HistoryResultCallback : ResultCallback<List<HistoryEntry<Double>>>