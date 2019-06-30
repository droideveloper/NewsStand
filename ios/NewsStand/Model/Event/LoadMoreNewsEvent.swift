//
//  LoadMoreNewsEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class LoadMoreNewsEvent: Event {

	private let source: Source
	private let page: Int
	
	init(source: Source, page: Int) {
    self.source = source
		self.page = page
  }

  override func toIntent(container: Container?) -> Intent {
		if let newsRepository = container?.resolve(NewsRepository.self) {
			return LoadMoreNewsIntent(source: source.id ?? "", page: page, newsRepository: newsRepository)
		}
		return super.toIntent(container: container)
  }
}
