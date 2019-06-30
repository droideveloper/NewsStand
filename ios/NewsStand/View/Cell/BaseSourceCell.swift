//
//  BaseSourceCell.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import UIKit
import MVICocoa
import RxGesture

class BaseSourceCell: UITableViewCell {
	
	public func clicks() -> Observable<UITapGestureRecognizer> {
		return self.contentView.rx.tapGesture()
			.when(.recognized)
	}
	
	public override func awakeFromNib() {
		super.awakeFromNib()
		
		self.backgroundColor = .backgroundColor
		self.contentView.backgroundColor = .backgroundColor
	}
	
	func bind(value: Source) {
		// no-opt
	}
}

