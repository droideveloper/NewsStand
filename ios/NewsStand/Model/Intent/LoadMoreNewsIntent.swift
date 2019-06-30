//
//  LoadMoreNewsIntent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa

class LoadMoreNewsIntent: ObservableIntent<NewsModel> {
	
	private let newsRepository: NewsRepository
	private let source: String
	private let page: Int
	
	init(source: String, page: Int, newsRepository: NewsRepository) {
		self.newsRepository = newsRepository
		self.page = page
		self.source = source
	}
	
	override func invoke() -> Observable<Reducer<NewsModel>> {
		return newsRepository.news(source: source, page: page)
			.concatMap(success(_ :))
			.catchError(failure(_:))
			.startWith(initial())
			.subscribeOn(MainScheduler.asyncInstance)
	}
	
	private func initial() -> Reducer<NewsModel> {
		return { o in o.copy(state: .operationOf(loadMore), data: [], totalResult: 0) }
	}
	
	private func success(_ resource: Resource<[News]>) -> Observable<Reducer<NewsModel>> {
		switch resource {
		case .success(let data, let totalResult): return Observable.of(
		{ o in o.copy(state: .operationOf(loadMore), data: data ?? [], totalResult: totalResult ?? 0)},
		{ o in o.copy(state: .idle, data: [], totalResult: 0) })
		case .failure(_, let message): return Observable.of(
		{ o in o.copy(state: .failure(MVIError.of(description: message ?? ""))) },
		{ o in o.copy(state: .idle) })
		}
	}
	
	private func failure(_ error: Error) -> Observable<Reducer<NewsModel>> {
		return Observable.of(
			{ o in o.copy(state: .failure(error)) },
			{ o in o.copy(state: .idle) })
	}
}
