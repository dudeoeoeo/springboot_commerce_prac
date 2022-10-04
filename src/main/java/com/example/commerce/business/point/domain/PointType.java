package com.example.commerce.business.point.domain;

public enum PointType {
    EVENT, TEXT_REVIEW, PHOTO_REVIEW, ORDER, CANCEL, RETURN, REFUND, EXPIRED;

    public static String toString(PointType type) {
        switch (type) {
            case EVENT:
                return "이벤트";
            case TEXT_REVIEW:
                return "리뷰";
            case PHOTO_REVIEW:
                return "사진 리뷰";
            case ORDER:
                return "주문";
            case CANCEL:
                return "취소";
            case RETURN:
                return "반품";
            case REFUND:
                return "환불";
            default:
                return "만료";
        }
    }
}
