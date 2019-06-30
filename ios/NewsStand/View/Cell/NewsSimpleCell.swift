//
//  NewsSimpleCell.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import MVICocoa
import Kingfisher
import RxSwift
import FisherExtensions

class NewsSimpleCell: BaseNewsCell {

	@IBOutlet weak var viewImage: UIImageView!
	@IBOutlet weak var viewTextTitle: UILabel!
	@IBOutlet weak var viewTextSpot: UILabel!
	@IBOutlet weak var viewTextState: UILabel!
	@IBOutlet weak var viewTextTime: UILabel!
	@IBOutlet weak var viewProgress: UIActivityIndicatorView!
	
  private let disposeBag = CompositeDisposeBag()

  override func prepareForReuse() {
    super.prepareForReuse()
    disposeBag.clear()
  }
	
	func bind(entity: News, newsRepository: NewsRepository) {
		invalidateProgress(false)
		
    viewTextTitle.text = entity.title
		viewTextSpot.text = entity.content
		viewImage.loadScale(entity.urlToImage?.toURL())
		
		let str: String
		if entity.hasBookmark {
			str = NSLocalizedString("news-remove-bookmark-title", comment: "")
		} else {
			str = NSLocalizedString("news-save-bookmark-title", comment: "")
		}
		viewTextState.text = str
		let timeFormatter = DateFormatter()
		timeFormatter.dateFormat = "HH:mm:ss"
		let timeStr: String

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
		let dateStr = entity.publishedAt ?? "1970-01-01T00:00:00Z"
		if let date = dateFormatter.date(from: dateStr) {
			timeStr = timeFormatter.string(from: date)
		} else {
			timeStr = "00:00:00"
		}
	
		viewTextTime.text = timeStr
		
		disposeBag += bindSelectNewsEvent(value: entity).subscribe(onNext: BusManager.send(event: ))
		disposeBag += bindBookmarkNewsEvent(value: entity).subscribe(onNext: BusManager.send(event: ))
		
		if !entity.hasBookmark {
			// we do check those content in localStorage if they exists
			disposeBag += newsRepository.hasBookmark(url: entity.url ?? "")
				.async()
				.subscribe(onNext: { [weak weakSelf = self] news in
					let hasBookmark = news.url == entity.url
					let str: String
					if hasBookmark {
						str = NSLocalizedString("news-remove-bookmark-title", comment: "")
					} else {
						str = NSLocalizedString("news-save-bookmark-title", comment: "")
					}
					weakSelf?.viewTextState.text = str
					var update = entity
					update.hasBookmark = hasBookmark
				})
		}
	}
	
	private func bindSelectNewsEvent(value: News) -> Observable<SelectNewsEvent> {
		let imageSource = viewImage.rx.tapGesture().when(.recognized)
		let titleSource = viewTextTitle.rx.tapGesture().when(.recognized)
		let spotSource = viewTextSpot.rx.tapGesture().when(.recognized)
		// bind those in here
		return Observable.merge(imageSource, titleSource, spotSource).map { _ in SelectNewsEvent(news: value) }
	}
	
	private func bindBookmarkNewsEvent(value: News) -> Observable<BookmarkNewsEvent> {
		return viewTextState.rx.tapGesture().when(.recognized)
			.map { _ in BookmarkNewsEvent(payload: value) }
			.do(onNext: { [weak weakSelf = self] _ in weakSelf?.invalidateProgress(true) } )
	}
	
	private func invalidateProgress(_ showProgress: Bool) {
		viewTextState.alpha = showProgress ? 0 : 1
		viewProgress.alpha = showProgress ? 1 : 0
		if showProgress {
			viewProgress.startAnimating()
		} else {
			viewProgress.stopAnimating()
		}
	}
}

extension String {
	
	public func toURL() -> URL?{
		return URL(string: self)
	}
}
