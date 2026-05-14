import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Spinner } from '@/components/ui/spinner';
import { useCreateStore } from '@/hooks/useStore';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import z from 'zod';

const storeSchema = z.object({
  name: z.string().min(3),
  address: z.string().min(10, 'Address too short').optional(),
  phone: z
    .string()
    .regex(/^[6-9]\d{9}$/, 'Enter a valid 10-digit Indian mobile number')
    .optional(),
  gstNumber: z.string().optional(),
});
type CreateStoreData = z.infer<typeof storeSchema>;
export default function StoreSetup() {
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm<CreateStoreData>({ resolver: zodResolver(storeSchema) });

  const navigate = useNavigate();

  const { mutate: createStoreMutate, isPending } = useCreateStore(() =>
    navigate('/'),
  );

  const onSubmit = (data: CreateStoreData) => {
    createStoreMutate(data);
  };

  return (
    <div className="min-h-screen flex items-center justify-center">
      <Card className="w-full max-w-lg shadow-lg">
        <CardHeader className="space-y-1 text-center">
          <CardTitle className="text-2xl font-bold">Setup Your Store</CardTitle>
          <CardDescription>
            Tell us about your store to get started
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div className="space-y-1">
              <Label htmlFor="name">Store Name *</Label>
              <Input
                id="name"
                placeholder="Sharma General Store"
                {...register('name')}
              />
              {errors.name && (
                <p className="text-xs text-red-500">{errors.name.message}</p>
              )}
            </div>
            <div className="space-y-1">
              <Label htmlFor="address">Address</Label>
              <Input
                id="address"
                placeholder="123, MG Road, Delhi"
                {...register('address')}
              />
              {errors.address && (
                <p className="text-xs text-red-500">{errors.address.message}</p>
              )}
            </div>
            <div className="space-y-1">
              <Label htmlFor="phone">Phone</Label>
              <Input
                id="phone"
                placeholder="98765 43210"
                {...register('phone')}
              />
              {errors.phone && (
                <p className="text-xs text-red-500">{errors.phone.message}</p>
              )}
            </div>
            <div className="space-y-1">
              <Label htmlFor="gstNumber">GST Number</Label>
              <Input
                id="gstNumber"
                placeholder="22AAAAA0000A1Z5"
                {...register('gstNumber')}
              />
            </div>
            <Button type="submit" className="w-full" disabled={isPending}>
              {isPending ? <Spinner /> : 'Create Store'}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
