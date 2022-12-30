import { IReview } from 'app/shared/model/review.model';

export interface IPostRack {
  id?: number;
  longitude?: number | null;
  latitude?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  reviews?: IReview[] | null;
}

export const defaultValue: Readonly<IPostRack> = {};
