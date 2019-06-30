//
//  BookmarkNewsIntent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class BookmarkNewsIntent: ObservableIntent<NewsModel> {

	private let state: Int
	private let newsRepository: NewsRepository
	private let payload: News
	
	init(payload: News, newsRepository: NewsRepository) {
		self.newsRepository = newsRepository
		self.payload = payload
		if payload.hasBookmark {
			self.state = removeBookmark
		} else {
			self.state = saveBookmark
		}
  }

  override func invoke() -> Observable<Reducer<NewsModel>> {
		let repoSource: Observable<News>
		if payload.hasBookmark {
			repoSource = newsRepository.removeBookmark(news: payload)
		} else {
			repoSource = newsRepository.addBookmark(news: payload)
		}
    return repoSource.concatMap(success(_ :))
      .catchError(failure(_ :))
      .startWith(initial())
			.delay(RxTimeInterval.milliseconds(500), scheduler: MainScheduler.asyncInstance)
      .subscribeOn(MainScheduler.asyncInstance)
  }

  private func initial() -> Reducer<NewsModel> {
		let state = self.state
		return { o in o.copy(state: .operationOf(state), changeState: .empty) }
  }

  private func success(_ data: News) -> Observable<Reducer<NewsModel>> {
		let state = self.state
		return Observable.of(
			{ o in o.copy(state: .operationOf(state), changeState: data) },
			{ o in o.copy(state: .idle, changeState: .empty) })
  }

  private func failure(_ error: Error) -> Observable<Reducer<NewsModel>> {
    return Observable.of(
      { o in o.copy(state: .failure(error)) },
      { o in o.copy(state: .idle) })
  }
}
