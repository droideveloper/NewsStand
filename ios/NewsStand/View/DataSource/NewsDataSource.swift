//
//  NewsDataSource.swift
//  NewsStand
//
//  Created by Fatih Şen on 30.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import MVICocoa

class NewsDataSource: CollectionDataSource<News> {

	private let newsRepository: NewsRepository
	
	init(dataSet: ObservableList<News>, newsRepository: NewsRepository) {
		self.newsRepository = newsRepository
		super.init(dataSet: dataSet)
	}
	
  override func identifierAt(_ indexPath: IndexPath) -> String {
		return String(describing: NewsSimpleCell.self)
	}
	
	override func bind(_ cell: UICollectionViewCell, _ item: News) {
		if let cell = cell as? NewsSimpleCell {
			cell.bind(entity: item, newsRepository: newsRepository)
		}
	}
}
