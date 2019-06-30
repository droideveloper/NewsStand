//
//  BookmarkDaoImp.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa
import Persist

class BookmarkDaoImp: BookmarkDao, Loggable {
	
	private let persist = Persist.codable
	private let file = File.create(.document, path: nil, name: "bookmark.db")
	private var cache = [News]()
	
	private let disposeBag = CompositeDisposeBag()
	
	init() {
		// initial read this is important
		disposeBag += persist.readAsync(from: file, success: { [weak weakSelf = self] (data: [News]) in
			if !data.isEmpty {
				weakSelf?.cache.append(contentsOf: data)
			}
		}, error: { [weak weakSelf = self] error in
			weakSelf?.log(error)
			// on first launch we do not have any file
			if let cache = weakSelf?.cache, let file = weakSelf?.file {
				if let disposable = weakSelf?.persist.writeAsync(data: cache, to: file) {
					weakSelf?.disposeBag += disposable
				}
			}
		})
	}
	
	func update(news: News) -> Completable {
		if let index = cache.firstIndex(of: news) {
			cache[index] = news
			return persist.write(value: cache, to: file)
		}
		return Completable.empty()
	}
	
	func insert(news: News) -> Completable {
		if cache.firstIndex(of: news) != nil {
			return Completable.empty()
		}
		cache.append(news)
		return persist.write(value: cache, to: file)
	}
	
	func delete(news: News) -> Completable {
		if let index = cache.firstIndex(of: news) {
			cache.remove(at: index)
			return persist.write(value: cache, to: file)
		}
		return Completable.empty()
	}
	
	func bookmarks() -> Observable<[News]> {
		return Observable.of(cache)
	}
	
	func hasBookmark(url: String) -> Observable<News> {
		let news = cache.first { n in n.url == url } ?? .empty
		return Observable.of(news)
	}
	
	deinit {
		disposeBag.clear()
	}
}
