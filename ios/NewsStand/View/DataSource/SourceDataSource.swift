//
//  SoruceDataSource.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import MVICocoa

class SourceDataSource: TableDataSource<Source> {

  override func identifierAt(_ indexPath: IndexPath) -> String {
		return String(describing: SourceSimpleCell.self)
	}
	
	override func bind(_ cell: UITableViewCell, _ item: Source) {
		if let cell = cell as? SourceSimpleCell {
			cell.bind(value: item)
		}
	}
}
