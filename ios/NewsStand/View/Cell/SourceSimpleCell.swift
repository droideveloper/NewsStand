//
//  SourceSimpleCell.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa

class SourceSimpleCell: BaseSourceCell {
	
	@IBOutlet weak var viewTextTitle: UILabel!
	@IBOutlet weak var viewTextSpot: UILabel!
	
	private let disposeBag = CompositeDisposeBag()
	
	override func prepareForReuse() {
		super.prepareForReuse()
		disposeBag.clear()
	}
	
	override func bind(value: Source) {
		
		viewTextTitle.text = value.name
		viewTextSpot.text = value.description
		
		disposeBag += bindSelectSourceEvent(value: value).subscribe(onNext: BusManager.send(event: ))
	}
	
	private func bindSelectSourceEvent(value: Source) -> Observable<SelectSourceEvent> {
		return clicks().map { _ in SelectSourceEvent(source: value) }
	}
}
