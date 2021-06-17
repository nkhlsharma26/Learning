package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.IndexDetailResponse;
import com.nikhil.Intraday.equities.modal.ScripQuoteDetailResponse;

import java.util.concurrent.CompletableFuture;

public interface QuoteService {
    IndexDetailResponse getIndexQuote();
    CompletableFuture<ScripQuoteDetailResponse> getScripQuote(String symbolName);
}
