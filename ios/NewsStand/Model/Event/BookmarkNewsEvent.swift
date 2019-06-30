//
//  BookmarkNewsEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class BookmarkNewsEvent: Event {
	
	private let payload: News
	
	init(payload: News) {
		self.payload = payload
	}
	
	override func toIntent(container: Container?) -> Intent {
		if let newsRepository = container?.resolve(NewsRepository.self) {
			return BookmarkNewsIntent(payload: payload, newsRepository: newsRepository)
		}
		return super.toIntent(container: container)
	}
}
