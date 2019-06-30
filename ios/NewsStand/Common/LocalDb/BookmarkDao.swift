//
//  BookmarkDao.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

protocol BookmarkDao {
	
	func update(news: News) -> Completable
	func insert(news: News) -> Completable
	func delete(news: News) -> Completable
	
	func bookmarks() -> Observable<[News]>
	func hasBookmark(url: String) -> Observable<News>
}
