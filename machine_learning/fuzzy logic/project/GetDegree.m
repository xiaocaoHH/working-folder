function G = GetDegree(x,center,span,label)

%%
%label==0 Minmun; label==1 Prod;
[n,dim1] = size(x);
[m,dim2] = size(center);
[k,dim3] = size(span);

if dim1 ~= dim2
    disp('Data end centers must have same dimension');	return;
end

if dim2 ~= dim3
    disp('centers and span must have same dimension');	return;
end

dim = dim1;
G = zeros(n,m);

%%
for i = 1:n
    % find active rules;
    sign = ones(size(center));
    for j = 1:dim        
        sign(find(span(j)-abs(center(:,j)-x(i,j))<0),j)=0;
    end
    
    if dim==1
       index=find(sign==1);
    else
       index=find(sum(sign')==dim);
    end 
    
    % get Hi under different active rules;
    result=zeros(1,dim);
    if ~isempty(index)            
        for k=1:length(index)
            t=index(k);
            result(:)=0;
            
            %x locate in right side of center;
            pos1=find(x(i,:)>center(t,:)|x(i,:)==center(t,:));
            if ~isempty(pos1)
               result(pos1)=(center(t,pos1)+span(pos1)-x(i,pos1))./span(pos1);
            end
           
            %x locate in left side of center;
            pos2=find(x(i,:)<center(t,:));
            if ~isempty(pos2)
               result(pos2)=(x(i,pos2)-center(t,pos2)+span(pos2))./span(pos2);
            end
           
            %get the final result using different operator;
            if label==0
               G(i,t)=min(result);
            else
               G(i,t)=prod(result);
            end
        end
    end
    
end